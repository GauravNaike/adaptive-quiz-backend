import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../services/api";

function Signup() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSignup = async () => {
        try {
            const res = await api.post("/auth/signup", {
                username,
                password,
            });

            localStorage.setItem("token", res.data.token);
            navigate("/dashboard");
        } catch (err) {
            alert("Signup Failed");
        }
    };

    return (
        <div className="container">
            <div className="card">
                <h2>Signup</h2>

                <input
                    placeholder="Username"
                    onChange={(e) => setUsername(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Password"
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button onClick={handleSignup}>Signup</button>

                <p>
                    Already have account? <Link to="/">Login</Link>
                </p>
            </div>
        </div>
    );
}

export default Signup;
