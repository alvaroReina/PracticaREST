

export const notBlank = (str) => {
    return (str && typeof str === "string" && str.trim().length > 0) ? str.trim() : undefined;
}

export const isNumber = (num) => {
    return (num !== undefined && num !== null && typeof num === "number" && !isNaN(num)) ? num : undefined; 
}

export const isAllowed = (user, resourceEmail) => {
    return user.userrole === "ADMIN" || resourceEmail === user.email;
}