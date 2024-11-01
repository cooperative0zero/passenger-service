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
                        id       : 1,
                        fullName : 'First Middle Last',
                        email    : 'exampleNewEmail@mail.com',
                        rating   : 1f,
                        isDeleted: false,
                        phone    : '987654321'
                ]
        )
    }

    response {
        status OK()
        headers {
            contentType: "application/json"
        }
        body(
                [
                        id       : 1,
                        fullName : 'First Middle Last',
                        email    : 'exampleNewEmail@mail.com',
                        rating   : 1f,
                        isDeleted: false,
                        phone    : '987654321'
                ]
        )
    }
}
