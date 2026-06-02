@charset "UTF-8";

* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;

}

#product-page {
    margin: 200px 40px;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
    padding: 20px;
    text-align: center;
}

.produto-card {
    background-color: RGB(17, 17, 17);
    padding: 12px;
    border-radius: 8px;
    margin: 0;


}
#product-page img {
    width: 100%;
    height: auto;
    border-radius: 8px;
}
#product-page h2 {
    font-size: 1.5em;
    margin: 10px 0;
    text-align: center;
}

#product-page p {
    padding: 15px;
    border-radius: 8px;
    text-align: center;
}
#product-page a {
    display: inline-block;
    text-decoration: none;
    color: white;
    background-color: #ff6b00;
    width: 90%;
    padding: 10px 0;
    border-radius: 10px;
    transition:
        transform 0.35s ease,
        background-color 0.35s ease;

}
#product-page a:hover {
    transform: scale(1.07);
    background-color: #ff7513f6;
}

.produto-card {
    transition: transform 0.35s ease;

}
.produto-card:hover {
    transform: translateY(-8px) scale(1.03);
}

@media (max-width: 768px) {
    #product-page{
        grid-template-columns: repeat(2, 1fr);
    }

    #product-page h2{
        font-size: 1rem;
    }
    #product-page a {
        font-size: 0.9rem;
    }
}
