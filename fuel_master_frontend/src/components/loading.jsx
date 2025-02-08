import { motion } from "framer-motion"

const Loading = () => {
    return (
        <div className="flex items-center justify-center h-[calc(100vh-270px)]">
            <motion.svg width="200" height="200" viewBox="0 0 200 200" initial="hidden" animate="visible">
                {[0, 1, 2].map((index) => (
                    <motion.circle
                        key={index}
                        cx="100"
                        cy="100"
                        r="80"
                        fill="none"
                        stroke={`rgba(245,109,36, ${0.7 - index * 0.2})`}
                        strokeWidth="6"
                        strokeLinecap="round"
                        variants={{
                            hidden: { pathLength: 0, opacity: 0 },
                            visible: {
                                pathLength: 1,
                                opacity: 1,
                                transition: {
                                    pathLength: {
                                        delay: index * 0.2,
                                        type: "spring",
                                        duration: 1.5,
                                        bounce: 0,
                                        repeat: Number.POSITIVE_INFINITY,
                                        repeatType: "loop",
                                        repeatDelay: 0.5,
                                    },
                                    opacity: { delay: index * 0.2, duration: 0.3 },
                                },
                            },
                        }}
                    />
                ))}
                <motion.circle
                    cx="100"
                    cy="100"
                    r="60"
                    fill="rgba(255, 128, 0, 0.2)"
                    variants={{
                        hidden: { scale: 0.8, opacity: 0 },
                        visible: {
                            scale: [0.8, 1.1, 0.8],
                            opacity: [0.3, 0.1, 0.3],
                            transition: {
                                duration: 2,
                                repeat: Number.POSITIVE_INFINITY,
                                repeatType: "loop",
                            },
                        },
                    }}
                />
            </motion.svg>
        </div>
    )
}

export default Loading