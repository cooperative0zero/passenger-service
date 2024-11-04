package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        url("/api/v1/passengers")
        method PUT()
        headers {
            contentType applicationJson()
        }
        body(
                [
                        id       : 999,
                        fullName : 'First Middle Last',
                        email    : 'example@mail.com',
                        rating   : 1f,
                        isDeleted: false,
                        phone    : '987654321'
                ]
        )
    }

    response {
        status NOT_FOUND()
        headers {
            contentType: applicationJson()
        }
        body(
                [
                        statusCode: 404,
                        customMessage: 'Passenger with id = 999 not exists'
                ]
        )
    }
}
