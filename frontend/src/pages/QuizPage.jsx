import { useEffect, useState } from "react";
import api from "../services/api";

function Quiz() {
    const [question, setQuestion] = useState(null);
    const userId = 1; // temporarily hardcoded (change later)

    useEffect(() => {
        fetchQuestion();
    }, []);

    const fetchQuestion = async () => {
        try {
            const res = await api.get(`/v1/quiz/next?userId=${userId}`);
            setQuestion(res.data);
        } catch (err) {
            console.log(err);
        }
    };

    const handleAnswer = async (answer) => {
        try {
            const res = await api.post("/v1/quiz/answer", {
                userId,
                questionId: question.id,
                answer,
                idempotencyKey: Date.now().toString(),
            });

            alert(res.data.correct ? "Correct!" : "Wrong!");
            fetchQuestion();
        } catch (err) {
            console.log(err);
        }
    };

    if (!question) return <h2>Loading...</h2>;

    return (
        <div className="container">
            <div className="card">
                <h3>{question.prompt}</h3>

                {question.options?.map((opt, i) => (
                    <button key={i} onClick={() => handleAnswer(opt)}>
                        {opt}
                    </button>
                ))}
            </div>
        </div>
    );
}

export default Quiz;
