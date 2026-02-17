import { Link } from "react-router-dom";

function Navbar() {
    return (
        <div className="navbar">
            <h3>Adaptive Quiz</h3>

            <div>
                <Link to="/dashboard">Dashboard</Link>
                <Link to="/quiz">Quiz</Link>
                <Link to="/leaderboard">Leaderboard</Link>
            </div>
        </div>
    );
}

export default Navbar;