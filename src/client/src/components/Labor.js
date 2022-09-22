export default function Labor(props){
    return(
        <div className={`flex flex-row space-x-2 rounded-md w-full py-2 px-4 ${props.labor.complete ? "border-green-600" : "border-red-600"} border-l-8 `}>
            <h1>Task: {props.labor.task}</h1>
            <h1>Location: {props.labor.location}</h1>
            <h1>Cost: {props.labor.cost}</h1>
            <p>Created{props.labor.createdAt.substring(0,10)}</p>
            <p>Updated {props.labor.updatedAt.substring(0,10)}</p>
        </div>
    )
}