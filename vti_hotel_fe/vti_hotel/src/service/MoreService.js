import { url } from "./axios"

export const MoreService = {
    login(LoginRequest){
        return url.post("login", LoginRequest)
    },
    uploadImage(file, folder){
        const formData = new FormData();
        formData.append(file)
        formData.append(folder)
        return url.post("upImage", formData, {
            headers:{
                'Content-Type': 'multipart/form-data'
            }
        })
    }
}