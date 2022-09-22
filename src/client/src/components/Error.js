export default function Error(error){
    <div className="h-screen flex">
        <div className="mx-auto my-auto alert alert-error text-white"><span className="font-bold">{error.status}</span><h1> {error.error}</h1></div>
    </div>
}