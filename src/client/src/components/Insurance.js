export default function Insurance(props){
    return(
        <div className="bg-white rounded-md p-5 flex flex-col shadow-sm hover:shadow-lg">
            <h1>provider: {props.insurance.provider}</h1>
            <h1>policy: {props.insurance.policy}</h1>
            <h1>vin {props.insurance.vin}</h1>
            <div className="flex flex-row">
                <p>{props.insurance.createdAt.substring(0,10)}</p>
                <p>{props.insurance.updatedAt.substring(0,10)}</p>
            </div>
        </div>
    )
}