import { SERIES } from './cte';
import axios from 'axios';

const base = SERIES;


class SSerie {

    constructor(auth) {
        this.auth = auth;
    }

    async findAll(from, to) {

        from = from ? from : '';
        to = to ? to : '';

        let resp = await axios.get(`${base}?from=${from}&to=${to}`, {
            headers: {
                Authorization: this.auth,
            },
        })

        if (resp) {
            console.log('resp', resp)
        }
    }

    async findOne(id) {

    }

    async update() {

    }

    /**
     * Resuelve las relaciones y cambia las claves foráneas por las 
     * las entidaddes correspondientes
     */
    async populate(serie) {

    }
}