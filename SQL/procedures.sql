CREATE OR REPLACE FUNCTION add_top_level_catalog
(
	nimi TEXT, 
	kirjeldus TEXT, 
	looja INT, 
	yksus INT, 
	OUT uus_kataloog INT
)
AS $$
DECLARE
	pr product_catalog.product_catalog%TYPE;
	err_msg TEXT;

BEGIN
	SELECT product_catalog INTO pr FROM product_catalog WHERE name = nimi;
	IF NOT FOUND THEN
		INSERT INTO product_catalog	(
			name, description, created_by, created, updated_by, updated, struct_unit
		) VALUES (
			nimi, kirjeldus, looja, NOW(), looja, NOW(), yksus
		);
		SELECT last_value INTO uus_kataloog FROM s_product_catalog;
	ELSE 
		SELECT 0 INTO uus_kataloog;
	END IF;
		
	EXCEPTION WHEN raise_exception THEN 
		err_msg := SQLSTATE || ': ' || SQLERRM;
        RAISE EXCEPTION '%', err_msg ; 
		WHEN OTHERS THEN
		err_msg := SQLSTATE || ': ' || SQLERRM;
		RAISE EXCEPTION 'SQL: %', err_msg ;
		
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION add_sub_catalog
(
	nimi TEXT,
	kirjeldus TEXT,
	looja INT, 
	yksus INT, 
	vanem INT, 
	OUT uus_kataloog INT
)
AS $$
DECLARE
	pr product_catalog.product_catalog%TYPE;
	err_msg TEXT;

BEGIN
	SELECT product_catalog INTO pr FROM product_catalog WHERE name = nimi AND upper_catalog = vanem;
	IF NOT FOUND THEN
		INSERT INTO product_catalog (
			name, description, upper_catalog, created_by, created, updated_by, updated, struct_unit
		) VALUES (
			nimi, kirjeldus, vanem, looja, NOW(), looja, NOW(), yksus
		);
		SELECT last_value INTO uus_kataloog FROM s_product_catalog;
	ELSE 
		SELECT 0 INTO uus_kataloog;
	END IF;
		
	EXCEPTION WHEN raise_exception THEN 
		err_msg := SQLSTATE || ': ' || SQLERRM;
        RAISE EXCEPTION '%', err_msg ; 
		WHEN OTHERS THEN
		err_msg := SQLSTATE || ': ' || SQLERRM;
		RAISE EXCEPTION 'SQL: %', err_msg ;
		
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION edit_catalog
(
	id INT,
	nimi TEXT,
	kirjeldus TEXT,
	muutja INT, 
	OUT tulemus INT
)
AS $$
DECLARE
	pr product_catalog.product_catalog%TYPE;
	err_msg TEXT;

BEGIN
	SELECT product_catalog INTO pr FROM product_catalog WHERE product_catalog = id 
	AND nimi NOT IN (SELECT name FROM product_catalog WHERE product_catalog != id);
	IF FOUND THEN
		UPDATE product_catalog SET name = nimi, description = kirjeldus
		WHERE product_catalog = id;
		
		SELECT 1 INTO tulemus;
	ELSE 
		SELECT 0 INTO tulemus;
	END IF;
		
	EXCEPTION WHEN raise_exception THEN 
		err_msg := SQLSTATE || ': ' || SQLERRM;
        RAISE EXCEPTION '%', err_msg ; 
		WHEN OTHERS THEN
		err_msg := SQLSTATE || ': ' || SQLERRM;
		RAISE EXCEPTION 'SQL: %', err_msg ;
		
END;
$$ LANGUAGE plpgsql;
