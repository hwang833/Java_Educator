import { Link } from "react-router-dom";

/*
    Show this component when the user navigates to a link that is not in the router.
*/
export default function JavaEduNoMatch(props) {

    return (
        <div>
            <h2>That's a 404.</h2>
            <p>Uh oh, looks like you've taken a wrong turn!</p>
            <p>
                <Link to="/">Back to safety.</Link>
            </p>
        </div>
    );
}
