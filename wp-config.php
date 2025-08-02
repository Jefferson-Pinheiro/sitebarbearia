<?php
define( 'WP_CACHE', true );

/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the installation.
 * You don't have to use the web site, you can copy this file to "wp-config.php"
 * and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * Database settings
 * * Secret keys
 * * Database table prefix
 * * Localized language
 * * ABSPATH
 *
 * @link https://wordpress.org/support/article/editing-wp-config-php/
 *
 * @package WordPress
 */

// ** Database settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define( 'DB_NAME', 'u284142705_5nuHl' );

/** Database username */
define( 'DB_USER', 'u284142705_W7c53' );

/** Database password */
define( 'DB_PASSWORD', 'Pn12Hhpv82' );

/** Database hostname */
define( 'DB_HOST', '127.0.0.1' );

/** Database charset to use in creating database tables. */
define( 'DB_CHARSET', 'utf8' );

/** The database collate type. Don't change this if in doubt. */
define( 'DB_COLLATE', '' );

/**#@+
 * Authentication unique keys and salts.
 *
 * Change these to different unique phrases! You can generate these using
 * the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}.
 *
 * You can change these at any point in time to invalidate all existing cookies.
 * This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define( 'AUTH_KEY',          'J6,&EIX(BSW|;SCI*Jjp^h@aaTnXrK(vac.!pQbk# Re&5~^=F0mER0p)UFp0l]I' );
define( 'SECURE_AUTH_KEY',   'Xe9MC-Kx%A08&x~SiF+wQr>-/< PY`5Ib{C(9gW[Xlj/aUF2kmc7ZIZK$g@=xLxy' );
define( 'LOGGED_IN_KEY',     '^YGuaU(*!?vBk?cVM)v;SgrLY&UsG!6Ztkuy6hgqetJJZYX=QYB2:]w8JwR&*hH@' );
define( 'NONCE_KEY',         'm|,dDT!Q%uv?gY%PfPiWlP809>0U|6OqzpSj,j8@j=K($5wtCSTCM}~ fewY}C=8' );
define( 'AUTH_SALT',         'Y<2xERdiaLHEm2zTr`)7w>i9qVsqv$>ynNMx@<x`mtYp)#gJTmE{;O7y=?PW94?i' );
define( 'SECURE_AUTH_SALT',  '_|u!T^5z7+81yV1/G9,JeL*?kIA=U!=uukxWz:sU:/6qwTo*q4E(0ywRVZy,c* x' );
define( 'LOGGED_IN_SALT',    'wJ56Oac:!~]m57FY%<59Tb2c*8s:f4q3(~vW7qkQ3bG[Xi^M`6z7#ls@tx{N99TZ' );
define( 'NONCE_SALT',        'T8/nG&~#I<5fInB|Tf>UaXja7}K)odI+<Ovmn]aNUxb!JW,?Rf:lnEn4)-9x%!bF' );
define( 'WP_CACHE_KEY_SALT', '6IYs/21s=rvoc^{!O][CRdk5f4&)c:th-#^rLnN<(8[+C-x/D_@IYBSkzlnY,uVZ' );


/**#@-*/

/**
 * WordPress database table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix = 'wp_';


/* Add any custom values between this line and the "stop editing" line. */



/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the documentation.
 *
 * @link https://wordpress.org/support/article/debugging-in-wordpress/
 */
if ( ! defined( 'WP_DEBUG' ) ) {
	define( 'WP_DEBUG', false );
}

define( 'FS_METHOD', 'direct' );
define( 'COOKIEHASH', 'a76bfcfb4f71670b66647ca0137bf39a' );
define( 'WP_AUTO_UPDATE_CORE', 'minor' );
/* That's all, stop editing! Happy publishing. */

/** Absolute path to the WordPress directory. */
if ( ! defined( 'ABSPATH' ) ) {
	define( 'ABSPATH', __DIR__ . '/' );
}

/** Sets up WordPress vars and included files. */
require_once ABSPATH . 'wp-settings.php';
