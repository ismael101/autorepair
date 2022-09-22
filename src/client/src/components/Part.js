export default function Part(props){
    return(
        <div className={`flex flex-row space-x-2 rounded-md w-full py-2 px-4 ${props.part.ordered ? "border-green-600" : "border-red-600"} border-l-8 `}>
            <h1>Title: {props.part.task}</h1>
            <h1>Location: {props.part.location}</h1>
            <h1>Cost: {props.part.cost}</h1>
            <p>Created: {props.part.createdAt.substring(0,10)}</p>
            <p>Updated: {props.part.updatedAt.substring(0,10)}</p>
        </div>
    )
}