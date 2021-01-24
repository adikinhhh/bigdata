import axios from 'axios';

const URL = '';

export const fetchParkingLots = async (query) => {
    const { data } = await axios.get(URL, {
        params: {
        }
    });

    return data;
}