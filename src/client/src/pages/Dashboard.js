import { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'

export default function Dashboard(){
    const { works, isLoading, isSuccess, isError, message } = useSelector(
        (state) => state.work
    )
    const navigate = useDispatch()
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(works)
    },[navigate])

    useEffect(() => {
        
    },[works])


    if(isLoading){
        return(
            <div className=''>
                
            </div>
        )
    }


    return(
        <div className='flex'>

        </div>
    )

}