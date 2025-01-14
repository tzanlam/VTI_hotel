import { url } from "./axios"

export const MoreService = {
    login(LoginRequest){
        return url.post("login", LoginRequest)
    },
    uploadImage(uploadRequest){
        return url.post("upImage", uploadRequest, {
            headers:{
                'Content-Type': 'multipart/form-data'
            }
        })
    },
    sendMailSupported(email, subject, body){
        return url.post(`sendMail?email=${email}&subject=${subject}&body=${body}`)
    }
}