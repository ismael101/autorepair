export default function Labor(props){
    return(
        <div className={`flex flex-row space-x-2 rounded-md w-full py-2 px-4`}>
            <div className={`h-5 w-5 rounded-lg ${props.part.ordered ? "bg-green-600" : "bg-red-600"}`}/>
            <h1>Task: {props.labor.task}</h1>
            <h1>Location: {props.labor.location}</h1>
            <h1>Cost: {props.labor.cost}</h1>
            <p>Created{props.labor.createdAt.substring(0,10)}</p>
            <p>Updated {props.labor.updatedAt.substring(0,10)}</p>
        </div>
    )
}