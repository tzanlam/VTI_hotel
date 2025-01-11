import { createBrowserRouter } from "react-router-dom";
import MainPage from "../page/MainPage";
import Profile from "../page/user/Profile";
import RoomPage from "../page/user/RoomPage";
import BookingPage from "../page/user/BookingPage";
import Contact from "../page/user/Contact";
import ListAccount from "../page/admin/ListAccount";
import RoomManager from "../page/admin/RoomManager";
import ListBooking from "../page/admin/ListBooking";
import ListFastBooking from "../page/admin/ListFastBooking";
import ListComment from "../page/admin/ListComment";
import BookingDetailsPage from "../page/user/BookingDetailsPage";

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
      },
      {
        path: "/accounts",
        element: <ListAccount />
      },
      {
        path: "/roomManager",
        element: <RoomManager />
      },
      {
        path: "/listBooking",
        element: <ListBooking />
      },
      {
        path: "/listFastBooking",
        element: <ListFastBooking />
      },
      {
        path: "/listComment",
        element: <ListComment />
      },
      {
        path: "/booking-details/:id",
        element: <BookingDetailsPage/>
      }
    ]
  }
]);
