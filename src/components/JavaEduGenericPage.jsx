import { Outlet } from "react-router-dom";
import { useState, useEffect } from "react";

import JavaEduNavbar from "./nav/JavaEduNavbar";
import JavaEduGuestNavbar from "./nav/JavaEduGuestNavbar";
import JavaEduDataContext from "../contexts/JavaEduDataContext";

/*
    This component is what will be shown on every page. Includes a navbar on top. Child components are loaded inside <outlet />
*/
export default function JavaEduGenericPage() {

    const [signedIn, setSignedIn] = useState(false);

    //keep the user signed in if they have local storage data
    useEffect(() => {
        if(localStorage.getItem("user")) {
            setSignedIn(true)
        }
        else {
            setSignedIn(false)
        }
    }, [])

    return <div>
        {
            signedIn ?

            <JavaEduNavbar />

            :

            <JavaEduGuestNavbar />

        }
        
        <div style={{ margin: "1rem" }}>
            <JavaEduDataContext.Provider value={[signedIn, setSignedIn]}>
                <Outlet />
            </JavaEduDataContext.Provider>
        </div>
    </div>
}