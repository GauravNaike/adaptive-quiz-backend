import { useState, useEffect } from "react";
import api from "../services/api";

function StatsPage() {
    const [stats, setStats] = useState(null);
    const [loading, setLoading] = useState(true);
    const userId = localStorage.getItem("userId");

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const response = await api.get(`/v1/quiz/stats?userId=${userId}`);
                setStats(response.data);
            } catch (error) {
                console.error("Error fetching stats:", error);
            } finally {
                setLoading(false);
            }
        };

        if (userId) fetchStats();
    }, [userId]);

    if (loading) return <div className="p-8 text-center">Loading stats...</div>;
    if (!stats) return <div className="p-8 text-center">No stats available</div>;

    return (
        <div className="container mx-auto p-4 max-w-2xl">
            <h2 className="text-3xl font-bold mb-6 text-center text-gray-800">Your Statistics</h2>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <StatCard title="Total Score" value={stats.totalScore?.toFixed(0)} />
                <StatCard title="Rank" value={`#${stats.rank}`} />
                <StatCard title="Accuracy" value={`${stats.accuracyPercent?.toFixed(1)}%`} />
                <StatCard title="Current Streak" value={stats.currentStreak} />
                <StatCard title="Max Streak" value={stats.maxStreak} />
                <StatCard title="Current Difficulty" value={stats.currentDifficulty} />
                <StatCard title="Avg Difficulty" value={stats.averageDifficulty?.toFixed(1)} />
                <StatCard title="Total Answers" value={stats.totalAnswers} />
            </div>
        </div>
    );
}

function StatCard({ title, value }) {
    return (
        <div className="bg-white p-6 rounded-lg shadow-md border hover:border-blue-300 transition-colors">
            <h3 className="text-gray-500 text-sm font-uppercase tracking-wider">{title}</h3>
            <p className="text-3xl font-bold text-gray-800 mt-2">{value}</p>
        </div>
    );
}

export default StatsPage;
