export default function Part(props){
    return(
        <div className={`flex flex-row space-x-2 rounded-md w-full py-2 px-4`}>
            <div className={`h-5 w-5 rounded-lg ${props.part.ordered ? "bg-green-600" : "bg-red-600"}`}/>
            <h1 className="font-bold">Title: {props.part.task}</h1>
            <h1 className="font-bold">Location: {props.part.location}</h1>
            <h1 className="font-bold">Cost: {props.part.cost}</h1>
            <p className="font-bold">Created: {props.part.createdAt.substring(0,10)}</p>
            <p className="font-bold">Updated: {props.part.updatedAt.substring(0,10)}</p>
            <></>
        </div>
    )
}