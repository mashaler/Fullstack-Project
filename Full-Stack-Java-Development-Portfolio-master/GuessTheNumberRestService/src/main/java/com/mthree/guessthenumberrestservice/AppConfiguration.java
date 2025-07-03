/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice;

import com.mthree.guessthenumberrestservice.dao.GameDao;
import com.mthree.guessthenumberrestservice.dao.GameDaoImpl;
import com.mthree.guessthenumberrestservice.dao.RoundDao;
import com.mthree.guessthenumberrestservice.dao.RoundDaoImpl;
import com.mthree.guessthenumberrestservice.service.Service;
import com.mthree.guessthenumberrestservice.service.ServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author josep
 */

@Configuration
public class AppConfiguration {

//   @Bean
//    public static GameDao getGameDao(){
//       return new GameDaoImpl();
//   }
//
//   @Bean
//    public static RoundDao getRoundDao(){
//       return new RoundDaoImpl();
//   }
//
//   @Bean
//    public static Service getService(GameDao gameDao, RoundDao roundDao){
//       return new ServiceImpl(gameDao, roundDao);
//   }

}
