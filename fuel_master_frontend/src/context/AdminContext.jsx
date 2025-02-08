import { createContext, useContext } from 'react';

export const AdminContext = createContext({
    user: null,
    userRole: null,
    setUser: () => { },
    setUserRole: () => { },
    logout: () => { },
    login: () => { },
});

export const useAdmin = () => {
    const context = useContext(AdminContext);
    if (context === undefined) {
        throw new Error('useAdmin must be used within an AdminProvider');
    }
    return context;
};