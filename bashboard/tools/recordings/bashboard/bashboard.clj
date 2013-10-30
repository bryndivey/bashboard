{:config {:order 0, :description "Basic site adds", :name :bashboard}
 :data
 [
  [:node-create [] :map]
  [:node-create [:main] :map]
  [:node-create [:main :counter] :map]
  [:transform-enable [:main :counter] :inc [{:io.pedestal.app.messages/topic [:counter]}]]
  [:node-create [:main :sites-count] :map]
  [:value [:main :sites-count] nil 3]
  [:node-create [:main :sites] :map]
  [:node-create [:main :sites "zadev1"] :map]
  [:value [:main :sites "zadev1"] nil {:owner "bryn"}]
  [:node-create [:main :sites "bais110"] :map]
  [:value [:main :sites "bais110"] nil {:owner "bryn"}]
  [:node-create [:main :sites "random-site-1"] :map]
  [:value [:main :sites "random-site-1"] nil {:owner "bryn"}]
  [:node-create [:pedestal] :map]
  [:node-create [:pedestal :debug] :map]
  [:node-create [:pedestal :debug :dataflow-time] :map]
  [:value [:pedestal :debug :dataflow-time] nil 6]
  :break
  [:node-create [:main :sites "random-site-2"] :map]
  [:value [:main :sites "random-site-2"] nil {:owner "bryn"}]
  [:value [:main :sites-count] 3 4]
  [:value [:pedestal :debug :dataflow-time] 6 12]
  :break
  [:node-create [:main :sites "random-site-3"] :map]
  [:value [:main :sites "random-site-3"] nil {:owner "bryn"}]
  [:value [:main :sites-count] 4 5]
  [:value [:pedestal :debug :dataflow-time] 12 8]
  :break
  [:node-create [:main :sites "random-site-4"] :map]
  [:value [:main :sites "random-site-4"] nil {:owner "bryn"}]
  [:value [:main :sites-count] 5 6]
  [:value [:pedestal :debug :dataflow-time] 8 10]
  :break
  [:node-create [:main :sites "random-site-5"] :map]
  [:value [:main :sites "random-site-5"] nil {:owner "bryn"}]
  [:value [:main :sites-count] 6 7]
  [:value [:pedestal :debug :dataflow-time] 10 8]
  :break
  [:node-create [:main :sites "random-site-6"] :map]
  [:value [:main :sites "random-site-6"] nil {:owner "bryn"}]
  [:value [:main :sites-count] 7 8]
  [:value [:pedestal :debug :dataflow-time] 8 14]
  :break
  [:node-create [:main :sites "random-site-7"] :map]
  [:value [:main :sites "random-site-7"] nil {:owner "bryn"}]
  [:value [:main :sites-count] 8 9]
  [:value [:pedestal :debug :dataflow-time] 14 12]
 ]}