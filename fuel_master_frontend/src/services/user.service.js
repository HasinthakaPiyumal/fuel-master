import apiService from "./api.service";

export const getUser = async () => {
    const user = await apiService.get("/user/authenticate");
    return user?.data?.data?.user;
}

export const getUserId = async () => {
    const user = await getUser();
    return user.id;
}

export const getUserRole = async () => {
    const user = await getUser();
    return user.role;
}

export const getUserEmail = async () => {
    const user = await getUser();
    return user.email;
}

export const getUserPhone = async () => {
    const user = await getUser();
    return user.phone;
}

export const getUserFullName = async () => {
    const user = await getUser();
    return user.fullName || user.firstName + " " + user.lastName;
}
