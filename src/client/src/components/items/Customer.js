import EditCustomer from "../modals/edit/EditCustomer"


export default function Customer(props){
    return( 
        <div className="bg-white rounded-md p-5 flex flex-col shadow-sm hover:shadow-lg border-orange-400">
            <h1>{props.customer.first} + {props.customer.last}</h1>
            <h1>{props.customer.email}</h1>
            <h1>{props.customer.phone}</h1>
            <div className="flex justify-between">
                <div>
                    <p>{props.customer.createdAt.subString(0,10)}</p>
                    <p>{props.customer.createdAt.subString(0,10)}</p>
                </div>
                <div> 
                    <EditCustomer/>
                </div>
            </div>
        </div>
    )
}