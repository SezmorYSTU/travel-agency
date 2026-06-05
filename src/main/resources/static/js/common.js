// Общие компоненты

// Загрузка header + nav
function loadHeader() {
    const headerHTML = `
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
            <div class="container-fluid">
                <a class="navbar-brand" href="/dashboard.html">🌍 TravelAgency</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto" id="nav-menu"></ul>
                    <div class="d-flex">
                        <span class="navbar-text text-light me-3" id="user-info"></span>
                        <button class="btn btn-outline-light btn-sm" onclick="logout()">🚪 Выйти</button>
                    </div>
                </div>
            </div>
        </nav>
    `;
    document.getElementById('header-container').innerHTML = headerHTML;
    loadNavigation();
}

// Загрузка навигации в зависимости от роли
async function loadNavigation() {
    try {
        const res = await fetch('/api/auth/me', { credentials: 'include' });
        if (!res.ok) throw new Error('Not authenticated');
        const user = await res.json();
        const role = user.role;

        document.getElementById('user-info').textContent = `${user.username} (${role})`;

    const navItems = {
        'ADMIN': [
            { text: '📍 Адреса', href: '/admin/addresses.html' },
            { text: '📘 Паспорта', href: '/admin/passports.html' },
            { text: '💼 Должности', href: '/admin/jobs.html' },
            { text: '🗺️ Направления', href: '/admin/directions.html' },
            { text: '👥 Сотрудники', href: '/admin/employees.html' },
            { text: '🏢 Туроператоры', href: '/admin/operators.html' },
            { text: '👤 Клиенты', href: '/admin/clients.html' },
            { text: '🧭 Туры', href: '/admin/tours.html' },
            { text: '📋 Бронирования', href: '/admin/bookings.html' },
            { text: '⭐ Предпочтения', href: '/admin/client-preferences.html' }
        ],
        'MANAGER': [
            { text: '🧭 Туры', href: '/manager/tours.html' },
            { text: '📋 Бронирования', href: '/manager/bookings.html' },
            { text: '👤 Клиенты', href: '/manager/clients.html' },
            { text: '🏢 Туроператоры', href: '/manager/operators.html' },
            { text: '🗺️ Направления', href: '/manager/directions.html' },
            { text: '📍 Адреса', href: '/manager/addresses.html' },
            { text: '📘 Паспорта', href: '/manager/passports.html' },
            { text: '⭐ Предпочтения', href: '/manager/client-preferences.html' }
        ],
        'OPERATOR': [
            { text: '🧭 Мои туры', href: '/operator/my-tours.html' },
            { text: '📋 Мои брони', href: '/operator/my-bookings.html' }
        ]
    };

        const menu = navItems[role] || [];
        const navHTML = menu.map(item => `
            <li class="nav-item">
                <a class="nav-link" href="${item.href}">${item.text}</a>
            </li>
        `).join('');

        document.getElementById('nav-menu').innerHTML = navHTML;
    } catch (e) {
        console.error('Failed to load navigation:', e);
    }
}

// Выход из системы
async function logout() {
    try {
        // Отправляем POST
        const response = await fetch('/logout', {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Accept': 'text/html'  // Spring Security ожидает этот заголовок для редиректа
            }
        });

        // Игнорируем статус ответа — редирект всё равно произойдёт
        window.location.href = '/login.html?logout';
    } catch (error) {
        console.warn('Logout fetch failed, forcing redirect:', error);
        // Даже если fetch упал — принудительно редиректим
        window.location.href = '/login.html?logout';
    }
}

// Проверка авторизации
async function checkAuth() {
    try {
        const res = await fetch('/api/auth/me', { credentials: 'include' });
        if (!res.ok) window.location.href = '/login.html';
        return await res.json();
    } catch (e) {
        window.location.href = '/login.html';
        return null;
    }
}


// Показ уведомления
function showAlert(message, type = 'info') {
    const alertHTML = `
        <div class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
    document.getElementById('alert-container').innerHTML = alertHTML;
    setTimeout(() => document.querySelector('.alert')?.remove(), 5000);
}

// Форматирование даты
function formatDate(dateString) {
    if (!dateString) return '-';
    return new Date(dateString).toLocaleDateString('ru-RU');
}

// Форматирование цены
function formatPrice(price) {
    if (!price) return '-';
    return new Intl.NumberFormat('ru-RU', { style: 'currency', currency: 'RUB' }).format(price);
}


document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('header-container')) {
        loadHeader();
    }
});