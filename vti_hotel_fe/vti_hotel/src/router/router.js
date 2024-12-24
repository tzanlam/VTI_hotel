import { createBrowserRouter } from "react-router-dom";
import MainPage from "../page/MainPage";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <MainPage />
    },
])