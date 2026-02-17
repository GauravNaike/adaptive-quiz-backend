import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../services/api";

function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const res = await api.post("/auth/login", {
                username,
                password,
            });

            localStorage.setItem("token", res.data.token);

            // If backend doesn't return userId,
            // you must decode token OR modify backend
            navigate("/dashboard");
        } catch (err) {
            alert("Login Failed");
        }
    };

    return (
        <div className="container">
            <div className="card">
                <h2>Login</h2>

                <input
                    placeholder="Username"
                    onChange={(e) => setUsername(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Password"
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button onClick={handleLogin}>Login</button>

                <p>
                    No account? <Link to="/signup">Signup</Link>
                </p>
            </div>
        </div>
    );
}

export default Login;
