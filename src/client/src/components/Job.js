export default function Job(props){
    return(
        <div className="bg-slate-600 hover:bg-slate-500 rounded-lg shadow-md hover:shadow-lg p-5">
            <h1 className="text-white mb-2">Description: {props.job.description}</h1>
            <h1 className="text-white mb-2">Parts: {props.job.parts.length}</h1>
            <h1 className="text-white mb-2">Labor: {props.job.labors.length}</h1>
            <h1 className="text-white mb-2">Status: {props.job.complete ? <div className="rounded-sm p-1 bg-green-400 text-green-700">complete</div> : <div className="rounded-sm p-1 bg-red-400 text-red-700">uncomplete</div>}</h1>
            <h1 className="text-white">Created: {props.job.createdAt}</h1>
        </div>
    )
}