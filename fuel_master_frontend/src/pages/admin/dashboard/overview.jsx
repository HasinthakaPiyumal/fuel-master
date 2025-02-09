import { Bar, BarChart, ResponsiveContainer, XAxis, YAxis } from "recharts";
import PropTypes from 'prop-types';

const daysOfWeek = ["sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"];

export function Overview({ data }) {
    const todayIndex = new Date().getDay();
    const tomorrow = daysOfWeek[(todayIndex + 1) % 7];
    const indexOfTomorrow = daysOfWeek.indexOf(tomorrow);
    const chartData = [];
    for (let i = 0; i < 7; i++) {
        const day = daysOfWeek[(indexOfTomorrow + i) % 7];
        const d = {
            name: day,
            total: data[day]
        }
        chartData.push(d);
    }
    return (
        <ResponsiveContainer width="100%" height={350}>
            <BarChart data={chartData}>
                <XAxis dataKey="name" stroke="#888888" fontSize={12} tickLine={false} axisLine={false} />
                <YAxis
                    stroke="#888888"
                    fontSize={12}
                    tickLine={false}
                    axisLine={false}
                    tickFormatter={(value) => `${value}L`}
                />
                <Bar dataKey="total" fill="#adfa1d" radius={[4, 4, 0, 0]} />
            </BarChart>
        </ResponsiveContainer>
    )
}

Overview.propTypes = {
    data: PropTypes.array.isRequired
}