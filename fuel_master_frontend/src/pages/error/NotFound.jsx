import { Button } from "@/components/ui/button"
import { CloudOff } from "lucide-react"
import { useNavigate } from "react-router-dom";

export default function NotFound() {
    const navigate = useNavigate();
  return (
    <div className="flex flex-col items-center justify-center text-gray-800">
      <div className="max-w-2xl w-full text-center">
        <CloudOff className="mx-auto h-24 w-24 text-[hsl(11,95%,56%)] animate-bounce" />
        <h1 className="mt-8 text-6xl font-bold text-[hsl(11,95%,56%)]">404</h1>
        <p className="mt-4 text-3xl font-semibold">Oops! Page not found</p>
        <p className="mt-4 text-lg">The page you&apos;re looking for doesn&apos;t exist or has been moved.</p>
        <Button className="mt-4" onClick={() => navigate(-1)}>
          Go back home
        </Button>
      </div>
      <div className="absolute inset-0 -z-10 overflow-hidden">
        <div
          className="absolute left-[calc(50%-4rem)] top-10 -z-10 transform-gpu blur-3xl sm:left-[calc(50%-18rem)] lg:left-48 lg:top-[calc(50%-30rem)] xl:left-[calc(50%-24rem)]"
          aria-hidden="true"
        >
          <div
            className="aspect-[1108/632] w-[69.25rem] bg-gradient-to-r from-[hsl(11,95%,56%)] to-[hsl(11,95%,76%)] opacity-20"
            style={{
              clipPath:
                "polygon(73.6% 51.7%, 91.7% 11.8%, 100% 46.4%, 97.4% 82.2%, 92.5% 84.9%, 75.7% 64%, 55.3% 47.5%, 46.5% 49.4%, 45% 62.9%, 50.3% 87.2%, 21.3% 64.1%, 0.1% 100%, 5.4% 51.1%, 21.4% 63.9%, 58.9% 0.2%, 73.6% 51.7%)",
            }}
          ></div>
        </div>
      </div>
    </div>
  )
}

