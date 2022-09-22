export default function Vehicle(props){
    return(
        <div className={`flex flex-col space-y-2 rounded w-full p-4`}>
            <h1>Make: {props.labor.task}</h1>
            <h1>Model: {props.labor.location}</h1>
            <h1>Year: {props.labor.cost}</h1>
            <div className="flex flex-row">
                <p>{props.labor.createdAt.substring(0,10)}</p>
                <p>{props.labor.updatedAt.substring(0,10)}</p>
            </div>
        </div>
    )
}