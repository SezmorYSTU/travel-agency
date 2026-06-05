// Универсальный модуль для работы с таблицами и модальными окнами
const CrudManager = {
    config: {},

    // Инициализация страницы
    init(config) {
        this.config = config;
        this.loadTable();
    },

    // Загрузка данных и отрисовка таблицы
    async loadTable() {
        try {
            const res = await fetch(this.config.apiUrl, { credentials: 'include' });
            if (!res.ok) throw new Error('Доступ запрещён или сервер не отвечает');
            this.config.data = await res.json();
            this.renderTable();
            if (this.config.loadRelated) await this.loadRelatedData();
        } catch (e) {
            this.showAlert(`❌ Ошибка загрузки: ${e.message}`, 'danger');
        }
    },

    // Отрисовка строк таблицы
    renderTable() {
        const tbody = document.getElementById('crud-tbody');
        if (!tbody) return;
        tbody.innerHTML = '';

        this.config.data.forEach(item => {
            const tr = document.createElement('tr');
            let html = '';
            this.config.columns.forEach(col => {
                const val = this.resolvePath(item, col.field);
                html += `<td>${val !== null && val !== undefined ? val : '-'}</td>`;
            });
            // генерация кнопок действий:
            let actionsHtml = '';
            if (!this.config.hideEdit) {
                actionsHtml += `<button class="btn btn-sm btn-warning me-1" onclick="CrudManager.openModal('edit', ${item.code})">✏️</button>`;
            }
            if (!this.config.hideDelete) {
                actionsHtml += `<button class="btn btn-sm btn-danger" onclick="CrudManager.deleteItem(${item.code})">🗑️</button>`;
            }
            html += `<td>${actionsHtml || ''}</td>`;
            tr.innerHTML = html;
            tbody.appendChild(tr);
        });
    },

    // Вспомогательный метод для доступа к вложенным полям (jobTitle.name)
    resolvePath(obj, path) {
        return path.split('.').reduce((o, k) => (o && o[k] !== undefined) ? o[k] : null, obj);
    },

    // Загрузка связанных списков (должности, адреса и т.д.)
    async loadRelatedData() {
        for (const [key, api] of Object.entries(this.config.relatedApis || {})) {
            try {
                const res = await fetch(api, { credentials: 'include' });
                this.config.cache[key] = await res.json();
                this.populateSelect(key);
            } catch(e) { console.warn(`Не загружено ${key}`, e); }
        }
    },

    populateSelect(key) {
        const select = document.getElementById(`select-${key}`);
        if (!select || !this.config.cache[key]) return;
        const list = this.config.cache[key];
        // Если это селект сотрудника и пользователь — менеджер, фильтруем
//        if (key === 'employee' && CurrentUser.isManager() && CurrentUser.username) {
//            list = list.filter(emp => emp.login === CurrentUser.username);
//        }
        select.innerHTML = '<option value="">Выберите...</option>' +
            list.map(i => `<option value="${i.code}">${i.name || i.fullName || i.companyName || (i.series && i.number ? `${i.series} ${i.number}` : null) || i.city + ', ' + i.street }</option>`).join('');
    },

    // Открытие модального окна
    openModal(mode, id = null) {
        if (this.config.hideCreate && mode === 'create') {
            this.showAlert('Создание запрещено для вашей роли', 'warning');
            return;
        }
        this.config.mode = mode;
        this.config.editId = id;
        document.getElementById('crud-modal-title').textContent =
            mode === 'create' ? `➕ Добавить ${this.config.entityName}` : `✏️ Редактировать ${this.config.entityName}`;
        document.getElementById('crud-form').reset();
        document.getElementById('crud-form').classList.remove('was-validated');

        if (mode === 'edit' && id) {
            const item = this.config.data.find(e => e.code === id);
            if (item) {
                this.config.fields.forEach(f => {
                    const input = document.querySelector(`[name="${f.key}"]`);
                    if (input) input.value = this.resolvePath(item, f.key) || '';
                });
            }
        }
        new bootstrap.Modal(document.getElementById('crud-modal')).show();
    },

    // Сохранение (Create / Update)
    async saveItem() {
        const form = document.getElementById('crud-form');
        if (!form.checkValidity()) { form.classList.add('was-validated'); return; }

        const payload = {};
        this.config.fields.forEach(f => {
            let val = form.querySelector(`[name="${f.key}"]`).value;
            if (f.type === 'number') val = parseFloat(val) || 0;
            if (f.isRelation && val) payload[f.key] = { code: parseInt(val) };
            else payload[f.key] = val;
        });

        const isCreate = this.config.mode === 'create';
        const method = isCreate ? 'POST' : 'PUT';
        const url = isCreate ? this.config.apiUrl : `${this.config.apiUrl}/${this.config.editId}`;

        try {
            const res = await fetch(url, {
                method, headers: { 'Content-Type': 'application/json' }, credentials: 'include', body: JSON.stringify(payload)
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            bootstrap.Modal.getInstance(document.getElementById('crud-modal')).hide();
            this.showAlert('✅ Успешно сохранено', 'success');
            this.loadTable();
        } catch (e) {
            this.showAlert(`❌ Ошибка сохранения: ${e.message}`, 'danger');
        }
    },

    // Удаление
    async deleteItem(id) {
        if (!confirm('Удалить эту запись?')) return;
        try {
            const res = await fetch(`${this.config.apiUrl}/${id}`, { method: 'DELETE', credentials: 'include' });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            this.showAlert('🗑️ Запись удалена', 'success');
            this.loadTable();
        } catch (e) {
            this.showAlert(`❌ Ошибка удаления: ${e.message}`, 'danger');
        }
    },

    showAlert(msg, type) {
        const box = document.getElementById('crud-alert');
        box.innerHTML = `<div class="alert alert-${type} alert-dismissible fade show">${msg}<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>`;
        setTimeout(() => box.innerHTML = '', 5000);
    }
};

window.CrudManager = CrudManager;