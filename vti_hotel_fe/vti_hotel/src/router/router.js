import { createBrowserRouter } from "react-router-dom";
import MainPage from "../page/user/MainPage";
import Profile from "../page/user/Profile";
import RoomPage from "../page/user/RoomPage";
import BookingPage from "../page/user/BookingPage";
import Contact from "../page/user/Contact";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <MainPage />,
    children: [
      {
        path: "/myProfile/:accountId",
        element: <Profile />
      },
      {
        path: "/rooms",
        element: <RoomPage />
      },
      {
        path: "/booking",
        element: <BookingPage />
      },
      {
        path: "/contact",
        element: <Contact />
      }
    ]
  }
]);
