export default function Customer(props){
    return( 
        <div className="bg-white rounded-md p-5 flex flex-col shadow-sm hover:shadow-lg">
            <h1>{props.customer.first} + {props.customer.last}</h1>
            <h1>{props.customer.email}</h1>
            <h1>{props.customer.phone}</h1>
            <div className="flex flex-col">
                <p>{props.customer.createdAt.subString(0,10)}</p>
                <p>{props.customer.createdAt.subString(0,10)}</p>
            </div>
        </div>
    )
}