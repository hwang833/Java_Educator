import { BrowserRouter, Route, Routes } from "react-router-dom";

import JavaEduLanding from "./pages/JavaEduLanding"
import JavaEduSignIn from "./pages/JavaEduSignIn"
import JavaEduNoMatch from "./pages/JavaEduNoMatch"
import JavaEduGenericPage from "../JavaEduGenericPage";
import JavaEduScore from "./pages/JavaEduScore";
import JavaEduMazeDemoing from "./pages/JavaEduMazeDemo";
import JavaEduMazeProgram from "./pages/JavaEduMazeProgram";
import JavaEduMazeSandbox from "./pages/JavaEduMazeSandbox";
import JavaEduMazeEditor from "./pages/JavaEduMazeEditor";
import JavaEduInstructions from "./pages/JavaEduInstructions";
import JavaEduLearnMore from "./pages/JavaEduLearnMore";
import JavaEduSignOut from "./pages/JavaEduSignOut";

/*
    Sends the user to a specific component when routing to that link
*/
export default function JavaEduRouter() {
    return <BrowserRouter>
        <Routes>
            <Route path="/" element={<JavaEduGenericPage />}>
                <Route index element={<JavaEduLanding />} />
                <Route path="sign-in" element={<JavaEduSignIn />} />
                <Route path="sign-out" element={<JavaEduSignOut />} />
                <Route path="score" element={<JavaEduScore />} />
                {/*<Route path="mazedemo" element={<JavaEduMazeDemoing />} />*/}
                <Route path="mazeprogram" element={<JavaEduMazeProgram />} />
                <Route path="mazekeyboard" element={<JavaEduMazeSandbox />} />
                <Route path="mazeeditor" element={<JavaEduMazeEditor />} />
                <Route path="instructions" element={<JavaEduInstructions />} />
                <Route path="learn-more" element={<JavaEduLearnMore/>} />
                <Route path="*" element={<JavaEduNoMatch />} />
            </Route>
        </Routes>
    </BrowserRouter>
}
