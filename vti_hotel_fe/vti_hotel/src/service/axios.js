import axios from "axios";
import { notification } from "antd";

export const url = axios.create({
    baseURL: "http://localhost:8082/hotel/",
    timeout: 5000,
    headers: {
        "Content-Type": "application/json",
    },
});

url.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

url.interceptors.response.use(
    (response) => response,
    (error) => {
        if (!error.response) {
            notification.error({
                message: "Network Error",
                description: "Network error or server not reachable.",
            });
            return Promise.reject({ message: "Network error or server not reachable." });
        }

        switch (error.response.status) {
            case 401:
                notification.error({
                    message: "Unauthorized",
                    description: "You are not authorized to access this resource.",
                });
                break;
            case 403:
                notification.error({
                    message: "Forbidden",
                    description: "You do not have permission to access this resource.",
                });
                break;
            case 500:
                notification.error({
                    message: "Server Error",
                    description: "Something went wrong on the server.",
                });
                break;
            default:
                notification.error({
                    message: `Error ${error.response.status}`,
                    description: error.response.data?.message || "An error occurred.",
                });
        }
        return Promise.reject(error);
    }
);
