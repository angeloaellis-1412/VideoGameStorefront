package com.gcu.videogamestorefront.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a video game sold through the storefront.
 * Stores game information, inventory details, pricing,
 * active status, and optional image data.
 */
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String platform;

    @NotBlank
    private String genre;

    @NotNull
    private Integer releaseYear;

    @NotNull
    private BigDecimal price;

    @Min(0)
    private int stockQuantity;

    private String description;

    private boolean active = true;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String mainImageUrl;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl1;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl2;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl3;

    /**
     * Creates an empty Game object.
     * Required by JPA for entity creation.
     */
    public Game() {
    }

    /**
     * Creates a game using the supplied game information.
     *
     * @param title game title
     * @param platform game platform
     * @param genre game genre
     * @param releaseYear year the game was released
     * @param price game price
     * @param stockQuantity number of copies currently in stock
     * @param description game description
     * @param active determines whether the game is visible on the storefront
     */
    public Game(String title, String platform, String genre, Integer releaseYear,
                BigDecimal price, int stockQuantity, String description, boolean active) {
        this.title = title;
        this.platform = platform;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.active = active;
    }

    /**
     * Returns the unique game ID.
     *
     * @return game ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique game ID.
     *
     * @param id game ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the game title.
     *
     * @return game title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the game title.
     *
     * @param title game title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the game platform.
     *
     * @return game platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Sets the game platform.
     *
     * @param platform game platform
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * Returns the game genre.
     *
     * @return game genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the game genre.
     *
     * @param genre game genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Returns the release year.
     *
     * @return release year
     */
    public Integer getReleaseYear() {
        return releaseYear;
    }

    /**
     * Sets the release year.
     *
     * @param releaseYear release year
     */
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * Returns the game price.
     *
     * @return game price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the game price.
     *
     * @param price game price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Returns the current stock quantity.
     *
     * @return number of copies in stock
     */
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the stock quantity.
     *
     * @param stockQuantity number of copies in stock
     */
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Returns the game description.
     *
     * @return game description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the game description.
     *
     * @param description game description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns whether the game is active on the storefront.
     *
     * @return true if active, otherwise false
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets whether the game is active on the storefront.
     *
     * @param active active status
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the main image data for the game.
     *
     * @return main image data URL
     */
    public String getMainImageUrl() {
        return mainImageUrl;
    }

    /**
     * Sets the main image data for the game.
     *
     * @param mainImageUrl main image data URL
     */
    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    /**
     * Returns the first additional game image.
     *
     * @return first additional image data URL
     */
    public String getImageUrl1() {
        return imageUrl1;
    }

    /**
     * Sets the first additional game image.
     *
     * @param imageUrl1 first additional image data URL
     */
    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    /**
     * Returns the second additional game image.
     *
     * @return second additional image data URL
     */
    public String getImageUrl2() {
        return imageUrl2;
    }

    /**
     * Sets the second additional game image.
     *
     * @param imageUrl2 second additional image data URL
     */
    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    /**
     * Returns the third additional game image.
     *
     * @return third additional image data URL
     */
    public String getImageUrl3() {
        return imageUrl3;
    }

    /**
     * Sets the third additional game image.
     *
     * @param imageUrl3 third additional image data URL
     */
    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }
}