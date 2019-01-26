<?php
/*
Plugin Name: Automatic Post from Contact Form
Plugin URL: https://www.red-blue.it/
Version: 0.1
Author: Angelo Pascadopoli
Description: Crea un post automaticamente dopo il submit di un form di Contact Form 7. Creato per le serate di BailaLoco.it.
*/

  function save_posted_data( $posted_data ) {

    $months = array(
      '01' => 'Gennaio', 
      '02' => 'Febbraio', 
      '03' => 'Marzo', 
      '04' => 'Aprile',
      '05' => 'Maggio', 
      '06' => 'Giugno', 
      '07' => 'Luglio', 
      '08' => 'Agosto',
      '09' => 'Settembre', 
      '10' => 'Ottobre', 
      '11' => 'Novembre',
      '12' => 'Dicembre');

    $day_names = array(
      'Monday' => 'Lunedì',
      'Tuesday' => 'Martedì',
      'Wednesday' => 'Mercoledì',
      'Thursday' => 'Giovedì',
      'Friday' => 'Venerdì',
      'Saturday' => 'Sabato',
      'Sunday' => 'Domenica');
    
    $regioni = array(
      'Valle d\'Aosta' => '286',
      'Piemonte' => '281',
      'Liguria' => '278',
      'Lombardia' => '279',
      'Veneto' => '190',
      'Trentino-Alto Adige' => '284',
      'Friuli-Venezia Giulia' => '277',
      'Emilia-Romagna' => '276',
      'Toscana' => '283',
      'Umbria' => '285',
      'Marche' => '100',
      'Lazio' => '165',
      'Abruzzo' => '274',
      'Molise' => '280',
      'Campania' => '135',
      'Puglia' => '99',
      'Basilicata' => '275',
      'Calabria' => '251',
      'Sicilia' => '250',
      'Sardegna' => '282');

    // 1 - serate, 3 - scuole di ballo
    if (isset($posted_data['text-529'])) {
      $category = 3;
      $author = 4;
    } else {
      $category = 1;
      $author = 2;
    }

    $formatted_data = date_format(date_create($posted_data['date-438']), 'l, d-m-Y');
    list($day_name, $date) = explode(',', $formatted_data);
    list($year, $month, $day) = explode('-', $posted_data['date-438']);      
    $day_name = $day_names[$day_name];
    $data = $day . ' ' . $months[$month] . ' ' . $year;

    if (isset($posted_data['text-529'])) { // Scuole di ballo
      $title = $posted_data['text-529'] . ' - ' . $posted_data['text-193'] . ' (' . $posted_data['menu-755'] . ')';
      $content = 'Indirizzo:' . "\n\n" .
                 $posted_data['text-229'] . ' - ' . $posted_data['text-193'] . ' (' . $posted_data['menu-755'] . ')' . "\n\n" . 
                 'Maestri:' . "\n\n" . 
                 $posted_data['text-123'] . "\n\n" . 
                 'Discipline:' . "\n\n" . 
                 $posted_data['text-124'] . "\n\n" . 
                 'Orari Corsi:' . "\n\n";
                 if (isset($posted_data['menu-719']) && $posted_data['menu-719'] != $posted_data['menu-720']) {
                    $content = $content . 'Lunedì dalle ore ' . $posted_data['menu-719'] . ' alle ore ' . $posted_data['menu-720'] . "\n\n";
                 }
                 if (isset($posted_data['menu-721']) && $posted_data['menu-721'] != $posted_data['menu-722']) {
                    $content = $content . 'Martedì dalle ore ' . $posted_data['menu-721'] . ' alle ore ' . $posted_data['menu-722'] . "\n\n";
                 }
                 if (isset($posted_data['menu-723']) && $posted_data['menu-723'] != $posted_data['menu-724']) {
                    $content = $content . 'Mercoledì dalle ore ' . $posted_data['menu-723'] . ' alle ore ' . $posted_data['menu-724'] . "\n\n";
                 }
                 if (isset($posted_data['menu-725']) && $posted_data['menu-725'] != $posted_data['menu-726']) {
                    $content = $content . 'Giovedì dalle ore ' . $posted_data['menu-725'] . ' alle ore ' . $posted_data['menu-726'] . "\n\n";
                 }
                 if (isset($posted_data['menu-727']) && $posted_data['menu-727'] != $posted_data['menu-728']) {
                    $content = $content . 'Venerdì dalle ore ' . $posted_data['menu-727'] . ' alle ore ' . $posted_data['menu-728'] . "\n\n";
                 }
                 if (isset($posted_data['menu-729']) && $posted_data['menu-729'] != $posted_data['menu-730']) {
                    $content = $content . 'Sabato dalle ore ' . $posted_data['menu-729'] . ' alle ore ' . $posted_data['menu-730'] . "\n\n"; 
                 }
                 if (isset($posted_data['menu-731']) && $posted_data['menu-731'] != $posted_data['menu-732']) {
                    $content = $content . 'Domenica dalle ore ' . $posted_data['menu-731'] . ' alle ore ' . $posted_data['menu-732'] . "\n\n";
                 }
                 $content = $content . 'Contatti:' . "\n\n" . 
                 $posted_data['text-126']. "\n\n" .
                 '<figure><iframe src="https://www.google.it/maps/embed/Via+Capotagliata,+12,+74121+Taranto+TA/@40.4670246,17.2515412,17z/data=!3m1!4b1!4m5!3m4!1s0x134702db3b0c8d49:0x74e1665afd8ac17a!8m2!3d40.4670246!4d17.2537299" width="600" height="450" allowfullscreen=""></iframe></figure>';
      $tags = array($posted_data['text-529'], $posted_data['text-193'], $posted_data['menu-755'], $posted_data['text-124']);
    } else { // Serate
      $title = $posted_data['text-193'] . ' (' . $posted_data['text-225'] . ') - ' . $posted_data['text-229'] . ' - ' . $day_name . ', ' . $data;
      $content = 'Data: ' . $day_name . ', ' . $data . "\n\n" . 
                  'Start ore: ' . $posted_data['menu-719'] . ':' . $posted_data['menu-87'] . "\n\n" . 
                  'Locale: ' . $posted_data['text-229'] . ' - ' . $posted_data['text-193'] . ' (' . $posted_data['text-225'] . ')' . "\n\n" . 
                  'Dj: ' . $posted_data['text-57'] . "\n\n" . 
                  'Esibizione e/o Evento: ' . $posted_data['text-425'] . "\n\n" . 
                  'Costo: € ' . $posted_data['number-545'] . "\n\n" . 
                  'Riduzione: ' . $posted_data['text-429'] . ' - entro ' . $posted_data['menu-707'] . ':' . $posted_data['menu-85'] . ' - € ' . $posted_data['number-923'];
      $tags = array($posted_data['text-229'], $posted_data['text-193'], $posted_data['text-57'], $posted_data['text-425'], $posted_data['text-429']);
    }

    $args = array(
      'post_type' => 'post',
      'post_status' => 'draft',
      'post_title' => $title,
      'post_content' => $content,
      'post_category' => array($category, $regioni[$posted_data['text-225']]),
      'post_author' => $author,
      'tags_input' => $tags
    );
    $post_id = wp_insert_post($args);

    if ( isset($_FILES["file-701"]) && $_FILES["file-701"]['tmp_name'] != '' ) {
      upload_immagine( $_FILES["file-701"], $post_id);
    }

    if(!is_wp_error($post_id)){
      //if( isset($posted_data['your-name']) ){
      //  update_post_meta($post_id, 'your-name', $posted_data['your-name']);
      //}
      //if( isset($posted_data['your-email']) ){
      //  update_post_meta($post_id, 'your-email', $posted_data['your-email']);
      //}
      //if( isset($posted_data['your-subject']) ){
      //  update_post_meta($post_id, 'your-subject', $posted_data['your-subject']);
      //}
      //if( isset($posted_data['your-message']) ){
      //  update_post_meta($post_id, 'your-message', $posted_data['your-message']);
      //}
      return $posted_data;
    }
  }

  add_filter( 'wpcf7_posted_data', 'save_posted_data' );

  function upload_immagine($file, $post_id = 0) {
    $upload = wp_upload_bits( $file['name'], null, file_get_contents( $file['tmp_name'] ) );
    $wp_filetype = wp_check_filetype( basename( $upload['file'] ), null );
    $wp_upload_dir = wp_upload_dir();
    $attachment = array(
      'guid' => $wp_upload_dir['baseurl'] . _wp_relative_upload_path( $upload['file'] ),
      'post_mime_type' => $wp_filetype['type'],
      'post_title' => preg_replace('/\.[^.]+$/', '', basename( $upload['file'] )),
      'post_content' => '',
      'post_status' => 'inherit'
    );
    $attach_id = wp_insert_attachment( $attachment, $upload['file'], $post_id );
  
    require_once(ABSPATH . 'wp-admin/includes/image.php');
  
    $attach_data = wp_generate_attachment_metadata( $attach_id, $upload['file'] );
    wp_update_attachment_metadata( $attach_id, $attach_data );

    set_post_thumbnail( $post_id, $attach_id );
  }
?>
