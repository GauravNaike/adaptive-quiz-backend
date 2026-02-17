import { useNavigate } from "react-router-dom";

function Dashboard() {
    const navigate = useNavigate();

    return (
        <div className="container">
            <div className="card">
                <h2>Welcome to Adaptive Quiz</h2>

                <button onClick={() => navigate("/quiz")}>
                    Start Quiz
                </button>

                <button onClick={() => navigate("/leaderboard")}>
                    View Leaderboard
                </button>
            </div>
        </div>
    );
}

export default Dashboard;
