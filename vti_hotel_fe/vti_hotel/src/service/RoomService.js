import { url } from "./axios"

export const RoomService = {
    fetchRooms(){
        return url.get("findRooms")
    },

    fetchRoomById(roomId){
        return url.get(`findRoomById?roomId=${roomId}`)
    },

    createRoom(roomRequest){
        return url.post("createRoom", roomRequest)
    },

    updateRoom(roomId, roomRequest){
        return url.put(`updateRoom?roomId=${roomId}`, roomRequest)
    },

    changeRoomQuantity(roomId, roomRequest){
        return url.put(`changeRoomQuantity?roomId=${roomId}`, roomRequest)
    },

    changeRoomPrice(roomId, roomRequest){
        return url.put(`changeRoomPrice?roomId=${roomId}`, roomRequest)
    }
}