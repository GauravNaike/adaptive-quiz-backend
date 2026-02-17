import { useEffect, useState } from "react";
import api from "../services/api";

function Leaderboard() {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetchLeaderboard();
    }, []);

    const fetchLeaderboard = async () => {
        try {
            const res = await api.get("/v1/leaderboard/top");
            setData(res.data);
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <div className="container">
            <div className="card">
                <h2>Leaderboard</h2>

                {data.map((user, index) => (
                    <p key={index}>
                        {index + 1}. {user.username} - {user.score}
                    </p>
                ))}
            </div>
        </div>
    );
}

export default Leaderboard;
