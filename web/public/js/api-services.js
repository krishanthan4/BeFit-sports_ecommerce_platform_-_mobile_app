const AuthService = {
    async login(email, password, rememberMe = false) {
        return await api.post('/auth/login', { email, password, rememberMe });
    },

    async register(userData) {
        return await api.post('/auth/register', userData);
    },

    async logout() {
        return await api.post('/auth/logout');
    },

    async checkSession() {
        return await api.get('/auth/session');
    },

    async verify(code) {
        return await api.post('/auth/verify', { code });
    }
};

const ProductService = {
    async getProducts(params = {}) {
        return await api.get('/products', params);
    },

    async getProduct(id) {
        return await api.get(`/products/${id}`);
    },

    async searchProducts(query, filters = {}) {
        return await api.get('/products/search', { query, ...filters });
    },

    async getSellerProducts() {
        return await api.get('/products/seller');
    },

    async createProduct(productData) {
        return await api.post('/products', productData);
    },

    async updateProduct(id, productData) {
        return await api.put(`/products/${id}`, productData);
    }
};

const CartService = {
    async getCart() {
        return await api.get('/cart');
    },

    async addToCart(product_id, quantity) {
        return await api.post('/cart', { product_id, quantity });
    },

    async updateCartItem(id, quantity) {
        return await api.put(`/cart/${id}`, { quantity });
    },

    async removeFromCart(id) {
        return await api.delete(`/cart/${id}`);
    },

    async clearCart() {
        return await api.delete('/cart');
    }
};

const OrderService = {
    async getOrders(params = {}) {
        return await api.get('/orders', params);
    },

    async getOrder(id) {
        return await api.get(`/orders/${id}`);
    },

    async checkout(orderData) {
        return await api.post('/orders/checkout', orderData);
    },

    async updateOrderStatus(id, status) {
        return await api.put(`/orders/${id}/status`, { status });
    }
};

const WishlistService = {
    async getWishlist() {
        return await api.get('/wishlist');
    },

    async addToWishlist(product_id) {
        return await api.post('/wishlist', { product_id });
    },

    async removeFromWishlist(id) {
        return await api.delete(`/wishlist/${id}`);
    }
};

const UserService = {
    async getProfile() {
        return await api.get('/user/profile');
    },

    async updateProfile(profileData) {
        return await api.put('/user/profile', profileData);
    },

    async updateAddress(addressData) {
        return await api.put('/user/address', addressData);
    },

    async changePassword(currentPassword, newPassword) {
        return await api.put('/user/password', { current_password: currentPassword, new_password: newPassword });
    }
};

const CategoryService = {
    async getCategories() {
        return await api.get('/categories');
    },

    async getBrands() {
        return await api.get('/categories/brands');
    },

    async getModels(brandId) {
        return await api.get(`/categories/models/${brandId}`);
    }
};
