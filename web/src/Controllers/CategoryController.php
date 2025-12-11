<?php
namespace BeFit\Controllers;

use BeFit\Core\Request;
use BeFit\Core\Response;

/**
 * Category Controller
 * Handle categories, brands, models, and location data
 */
class CategoryController {
    
    /**
     * Get all categories
     * GET /api/v1/categories
     */
    public function index(Request $request) {
        $result = \Database::search("SELECT * FROM category ORDER BY cat_name ASC");
        $categories = [];
        
        while ($row = $result->fetch_assoc()) {
            $categories[] = $row;
        }
        
        Response::success($categories);
    }
    
    /**
     * Get category details
     * GET /api/v1/categories/{id}
     */
    public function show(Request $request, $params) {
        $id = $params['id'] ?? null;
        
        $result = \Database::search("SELECT * FROM category WHERE cat_id = ?", [$id]);
        
        if ($result->num_rows > 0) {
            $category = $result->fetch_assoc();
            
            // Get brands for this category
            $brandsResult = \Database::search(
                "SELECT b.* FROM brand b
                 INNER JOIN category_has_brand chb ON chb.brand_brand_id = b.brand_id
                 WHERE chb.category_cat_id = ?
                 ORDER BY b.brand_name ASC",
                [$id]
            );
            
            $brands = [];
            while ($row = $brandsResult->fetch_assoc()) {
                $brands[] = $row;
            }
            
            $category['brands'] = $brands;
            
            Response::success($category);
        }
        
        Response::notFound('Category not found');
    }
    
    /**
     * Get all countries
     * GET /api/v1/countries
     */
    public function getCountries(Request $request) {
        $result = \Database::search("SELECT * FROM country ORDER BY name ASC");
        $countries = [];
        
        while ($row = $result->fetch_assoc()) {
            $countries[] = $row;
        }
        
        Response::success($countries);
    }
    
    /**
     * Get provinces by country
     * GET /api/v1/provinces/{country_id}
     */
    public function getProvinces(Request $request, $params) {
        $countryId = $params['country_id'] ?? null;
        
        $result = \Database::search(
            "SELECT * FROM province WHERE country_id = ? ORDER BY province_name ASC",
            [$countryId]
        );
        
        $provinces = [];
        while ($row = $result->fetch_assoc()) {
            $provinces[] = $row;
        }
        
        Response::success($provinces);
    }
    
    /**
     * Get districts by province
     * GET /api/v1/districts/{province_id}
     */
    public function getDistricts(Request $request, $params) {
        $provinceId = $params['province_id'] ?? null;
        
        $result = \Database::search(
            "SELECT * FROM district WHERE province_province_id = ? ORDER BY district_name ASC",
            [$provinceId]
        );
        
        $districts = [];
        while ($row = $result->fetch_assoc()) {
            $districts[] = $row;
        }
        
        Response::success($districts);
    }
    
    /**
     * Get cities by district
     * GET /api/v1/cities/{district_id}
     */
    public function getCities(Request $request, $params) {
        $districtId = $params['district_id'] ?? null;
        
        $result = \Database::search(
            "SELECT * FROM city WHERE district_district_id = ? ORDER BY city_name ASC",
            [$districtId]
        );
        
        $cities = [];
        while ($row = $result->fetch_assoc()) {
            $cities[] = $row;
        }
        
        Response::success($cities);
    }
    
    /**
     * Get all brands
     * GET /api/v1/brands
     */
    public function getBrands(Request $request) {
        $result = \Database::search("SELECT * FROM brand ORDER BY brand_name ASC");
        $brands = [];
        
        while ($row = $result->fetch_assoc()) {
            $brands[] = $row;
        }
        
        Response::success($brands);
    }
    
    /**
     * Get brands by category
     * GET /api/v1/brands/{category_id}
     */
    public function getBrandsByCategory(Request $request, $params) {
        $categoryId = $params['category_id'] ?? null;
        
        $result = \Database::search(
            "SELECT b.* FROM brand b
             INNER JOIN category_has_brand chb ON chb.brand_brand_id = b.brand_id
             WHERE chb.category_cat_id = ?
             ORDER BY b.brand_name ASC",
            [$categoryId]
        );
        
        $brands = [];
        while ($row = $result->fetch_assoc()) {
            $brands[] = $row;
        }
        
        Response::success($brands);
    }
    
    /**
     * Get models by brand
     * GET /api/v1/models/{brand_id}
     */
    public function getModelsByBrand(Request $request, $params) {
        $brandId = $params['brand_id'] ?? null;
        
        $result = \Database::search(
            "SELECT m.*, mhb.id as model_has_brand_id FROM model m
             INNER JOIN model_has_brand mhb ON mhb.model_model_id = m.model_id
             WHERE mhb.brand_brand_id = ?
             ORDER BY m.model_name ASC",
            [$brandId]
        );
        
        $models = [];
        while ($row = $result->fetch_assoc()) {
            $models[] = $row;
        }
        
        Response::success($models);
    }
    
    /**
     * Get all colors
     * GET /api/v1/colors
     */
    public function getColors(Request $request) {
        $result = \Database::search("SELECT * FROM color ORDER BY clr_name ASC");
        $colors = [];
        
        while ($row = $result->fetch_assoc()) {
            $colors[] = $row;
        }
        
        Response::success($colors);
    }
    
    /**
     * Get all conditions
     * GET /api/v1/conditions
     */
    public function getConditions(Request $request) {
        $result = \Database::search("SELECT * FROM `condition` ORDER BY condition_id ASC");
        $conditions = [];
        
        while ($row = $result->fetch_assoc()) {
            $conditions[] = $row;
        }
        
        Response::success($conditions);
    }
}
