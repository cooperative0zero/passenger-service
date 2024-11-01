package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        url("/api/v1/passengers")
        method GET()
    }

    response {
        status OK()
        headers {
            contentType: applicationJson()
        }
        body(
                [
                    page: 0,
                    size: 10,
                    total: 2,
                    items: [
                            [
                                    id       : 1,
                                    fullName : 'First Middle Last',
                                    email    : 'example@mail.com',
                                    rating   : 1f,
                                    isDeleted: false,
                                    phone    : '987654321'
                            ],
                            [
                                    id       : 2,
                                    fullName : 'First2 Middle2 Last2',
                                    email    : 'example2@mail.com',
                                    rating   : 2f,
                                    isDeleted: false,
                                    phone    : '123456789'
                            ]
                    ]
                ],
        )
    }
}
