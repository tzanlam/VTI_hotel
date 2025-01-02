import { createBrowserRouter } from "react-router-dom";
import MainPage from "../page/MainPage";
import Profile from "../page/user/Profile";
import RoomPage from "../page/user/RoomPage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <MainPage />,
    children: [
      {
        path: "/myProfile/:accountId",  // Use :accountId as a dynamic route parameter
        element: <Profile />
      },
      {
        path: "/rooms",
        element: <RoomPage />
      }
    ]
  }
]);
