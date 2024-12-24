import { url } from "./axios"

export const MoreService = {
    login(LoginRequest){
        return url.post("login", LoginRequest)
    }
}