import { Skeleton } from "@/components/ui/skeleton";

export default function IconsSkeleton() {
    return (<>
        <div className="space-y-1">
            <Skeleton className="h-4 w-[150px]" />
            <Skeleton className="h-4 w-[100px]" />
        </div>
    </>)
}