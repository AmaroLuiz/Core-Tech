declare var axios: any;

export const api = axios.create({
    baseURL: "http://localhost:8080"
});

api.interceptors.request.use((config: any) => {
    const token = localStorage.getItem('token');

    if (token) {
        config.headers = config.headers || {};
        config.headers.Authorization = token;
    }
    return config;
});
