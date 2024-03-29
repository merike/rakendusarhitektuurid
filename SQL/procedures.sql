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
	upper INT;
	level INT;

BEGIN
	SELECT product_catalog INTO pr FROM product_catalog 
	WHERE upper_catalog IS NULL AND product_catalog = id;
	
	IF FOUND THEN
		level := 0;
	ELSE
		level := 1; 
	END IF;
	
	/* top-level */
	IF level = 0 THEN
		SELECT product_catalog INTO pr FROM product_catalog 
		WHERE 
		product_catalog = id 
		AND nimi NOT IN 
			(SELECT name FROM product_catalog WHERE product_catalog != id AND upper_catalog IS NULL)
		;
		
		/* ok to update */
		IF FOUND THEN
			UPDATE product_catalog SET name = nimi, description = kirjeldus
			WHERE product_catalog = id;
			
			SELECT 1 INTO tulemus;
		/* not ok to update */
		ELSE
			SELECT 0 INTO tulemus;
		END IF;
	
	/* sub-catalog */	
	ELSE
		SELECT product_catalog INTO pr FROM product_catalog 
		WHERE 
		product_catalog = id 
		AND nimi NOT IN 
			(SELECT name FROM product_catalog 
			WHERE product_catalog != id AND upper_catalog = 
				(SELECT upper_catalog FROM product_catalog WHERE product_catalog = id)
			)
		;
		
		/* ok to update */
		IF FOUND THEN
			UPDATE product_catalog SET name = nimi, description = kirjeldus
			WHERE product_catalog = id;
			
			SELECT 1 INTO tulemus;
		/* not ok to update */
		ELSE
			SELECT 0 INTO tulemus;
		END IF;
	END IF;
	
	EXCEPTION WHEN raise_exception THEN 
		err_msg := SQLSTATE || ': ' || SQLERRM;
        RAISE EXCEPTION '%', err_msg ; 
		WHEN OTHERS THEN
		err_msg := SQLSTATE || ': ' || SQLERRM;
		RAISE EXCEPTION 'SQL: %', err_msg ;
		
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION move_catalog
(
	kataloogId INT,
	sihtKataloog INT,
	OUT tulemus INT
)
AS $$
DECLARE
	pr product_catalog.product_catalog%TYPE;
	err_msg TEXT;

BEGIN
	/* forbid moving upper level catalog */
	SELECT product_catalog INTO pr FROM product_catalog WHERE product_catalog = kataloogId AND upper_catalog IS NULL;
	IF FOUND THEN
		SELECT 1 INTO tulemus;
	ELSE
		/* NOT allowing move to subcatalog, thus increasing tree depth */
		SELECT product_catalog INTO pr FROM product_catalog WHERE product_catalog = sihtKataloog AND upper_catalog IS NOT NULL;
		IF FOUND THEN
			SELECT 2 INTO tulemus;
		ELSE
			/* sub-catalogs with same name disallowed */
			SELECT product_catalog INTO pr FROM product_catalog WHERE upper_catalog = sihtKataloog 
			AND name = (SELECT name FROM product_catalog WHERE product_catalog = kataloogId);
			IF NOT FOUND THEN
				UPDATE product_catalog SET upper_catalog = sihtKataloog WHERE product_catalog = kataloogId;
				SELECT 0 INTO tulemus;
			ELSE 
				SELECT 3 INTO tulemus;
			END IF;
		END IF;
	END IF;
		
	EXCEPTION WHEN raise_exception THEN 
		err_msg := SQLSTATE || ': ' || SQLERRM;
        RAISE EXCEPTION '%', err_msg ; 
		WHEN OTHERS THEN
		err_msg := SQLSTATE || ': ' || SQLERRM;
		RAISE EXCEPTION 'SQL: %', err_msg ;
		
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION edit_product
(
	id INT,
	nimi TEXT,
	kirjeldus TEXT,
	kood TEXT,
	tootja INT,
	hind DOUBLE PRECISION,
	muutja INT, 
	OUT tulemus INT
)
AS $$
DECLARE
	pr product.product%TYPE;
	e enterprise.enterprise%TYPE;
	err_msg TEXT;

BEGIN
	SELECT product INTO pr FROM product 
	WHERE product = id;
	
	IF FOUND THEN
		SELECT enterprise INTO e FROM enterprise
		WHERE enterprise = tootja;
		
		IF FOUND THEN
			UPDATE product SET name = nimi, description = kirjeldus, code = kood, enterprise = tootja,
			price = hind, updated_by = muutja, updated = NOW() WHERE product = id;
			SELECT 0 INTO tulemus;
		ELSE
			SELECT 2 INTO tulemus;
		END IF;
	ELSE
		SELECT 1 INTO tulemus; 
	END IF;
		
	EXCEPTION WHEN raise_exception THEN 
		err_msg := SQLSTATE || ': ' || SQLERRM;
        RAISE EXCEPTION '%', err_msg ; 
		WHEN OTHERS THEN
		err_msg := SQLSTATE || ': ' || SQLERRM;
		RAISE EXCEPTION 'SQL: %', err_msg ;
		
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION add_product
(
	nimi TEXT,
	kirjeldus TEXT,
	kood TEXT,
	hind DOUBLE PRECISION, 
	tootja INT, 
	kataloog INT, 
	lisaja INT,
	OUT uus_toode INT
)
AS $$
DECLARE
	pr product_catalog.product_catalog%TYPE;
	en enterprise.enterprise%TYPE;
	err_msg TEXT;

BEGIN
	SELECT product_catalog INTO pr FROM product_catalog WHERE product_catalog = kataloog;
	IF FOUND THEN
		SELECT enterprise INTO en FROM enterprise WHERE enterprise = tootja;
		IF FOUND THEN
			INSERT INTO product (
				name, description, catalog, created_by, created, updated_by, updated, enterprise, price, code
			) VALUES (
				nimi, kirjeldus, kataloog, lisaja, NOW(), lisaja, NOW(), tootja, hind, kood
			);
			SELECT last_value INTO uus_toode FROM s_product;
		ELSE
			SELECT -2 INTO uus_toode;
		END IF;
	ELSE 
		SELECT -1 INTO uus_toode;
	END IF;
		
	EXCEPTION WHEN raise_exception THEN 
		err_msg := SQLSTATE || ': ' || SQLERRM;
        RAISE EXCEPTION '%', err_msg ; 
		WHEN OTHERS THEN
		err_msg := SQLSTATE || ': ' || SQLERRM;
		RAISE EXCEPTION 'SQL: %', err_msg ;
		
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION delete_product
(
	toode INT, 
	OUT tulemus INT
)
AS $$
DECLARE
	pr product.product%TYPE;
	err_msg TEXT;

BEGIN
	SELECT product INTO pr FROM product WHERE product = toode;
	IF FOUND THEN
		DELETE FROM product WHERE product = toode;
		DELETE FROM product_product_catalog WHERE product = toode;
		
		SELECT 0 INTO tulemus;
	ELSE 
		SELECT 1 INTO tulemus;
	END IF;
		
	EXCEPTION WHEN raise_exception THEN 
		err_msg := SQLSTATE || ': ' || SQLERRM;
        RAISE EXCEPTION '%', err_msg ; 
		WHEN OTHERS THEN
		err_msg := SQLSTATE || ': ' || SQLERRM;
		RAISE EXCEPTION 'SQL: %', err_msg ;
		
END;
$$ LANGUAGE plpgsql;
