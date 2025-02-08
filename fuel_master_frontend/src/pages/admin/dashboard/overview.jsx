import { Bar, BarChart, ResponsiveContainer, XAxis, YAxis } from "recharts";
import PropTypes from 'prop-types';
export function Overview({ data }) {
    const keys = Object.keys(data);
    const values = Object.values(data);
    const formattedData = keys.map((key, index) => ({
        name: key,
        total: values[index]
    }));
    return (
        <ResponsiveContainer width="100%" height={350}>
            <BarChart data={formattedData}>
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